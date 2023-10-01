const express = require('express')
const cors = require('cors')
const sqlite3 = require('sqlite3').verbose()
const nodemailer = require('nodemailer')

const app = express()
const port = 5000

const transporter = nodemailer.createTransport({
    service: 'Outlook',
    auth: {
        // TheSacTeamBoutique@outlook.com
        user: 'TheSacTeamBoutique@outlook.fr',
        pass: '@etu456...'
    }
})

app.use(cors({
    origin: 'http://localhost:8080',
    optionsSuccessStatus: 200
}))
app.use(express.json())

const db = new sqlite3.Database('data/database.db', (err) => {
    if (err) {
        console.error('Could not connect to database', err)
    } else {
        console.log('Connected to database')
    }
})

app.get('/produit', (req, res) => {
    db.all('SELECT * FROM produit', (err, rows) => {
        if (err) {
            console.error(err)
            res.status(500).json({ error: 'Internal server error' })
        } else {
            res.json(rows)
        }
    })
})

app.get('/searchQuery', (req, res) => {
    const { query } = req.query
    console.log('dans mon /searchQuery ', query)
    const sqlQuery = `SELECT * FROM produit WHERE nom LIKE '%${query}%'`

    db.all(sqlQuery, (err, rows) => {
        if (err) {
            console.error(err)
            res.status(500).json({ error: 'Internal server error' })
        } else {
            res.json(rows)
        }
    })
})

app.get('/produit/categorie', (req, res) => {
    const { query } = req.query
    const sqlQuery = `SELECT * FROM produit WHERE categorie_id LIKE '%${query}%'`

    db.all(sqlQuery, (err, rows) => {
        if (err) {
            console.error(err)
            res.status(500).json({ error: 'Internal server error' })
        } else {
            res.json(rows)
        }
    })
})

app.get('/utilisateurs', (req, res) => {
    db.all('SELECT * FROM utilisateur', (err, rows) => {
        if (err) {
            console.error(err)
            res.status(500).json({ error: 'Internal server error' })
        } else {
            res.json(rows)
        }
    })
})

app.post('/login', (req, res) => {
    const { email, password } = req.body

    db.all('SELECT * FROM utilisateur WHERE email = ? AND password = ?', [email, password], (err, rows) => {
        if (err) {
            console.error(err)
            res.status(500).json({ error: 'Internal server error' })
        } else {
            res.json(rows)
        }
    })
})
app.post('/register', (req, res) => {
    const { email, password } = req.body
    let retour = ''

    if (!email || !password) {
        retour = res.status(400).json({ error: 'Email et mot de passe sont obligatoires' })
    }

    db.get('SELECT * FROM utilisateur WHERE email = ?', [email], (err, existingUser) => {
        if (err) {
            console.error(err)
            retour = res.status(500).json({ error: 'Erreur du serveur' })
        }

        if (existingUser) {
            retour = res.status(409).json({ error: 'Cet email est déjà utilisé' })
        }

        db.run('INSERT INTO utilisateur (email, password) VALUES (?, ?)', [email, password], function (err) {
            if (err) {
                console.error(err)
                retour = res.status(500).json({ error: 'Erreur du serveur' })
            }

            retour = res.status(200).json({ message: 'Nouvel utilisateur créé avec l\'ID ' + this.lastID })
        })
    })
    return retour
})

app.post('/envoyer-email', (req, res) => {
    const { destinataire, sujet, contenu } = req.body

    const mailOptions = {
        from: 'TheSacTeamBoutique@outlook.fr',
        to: destinataire,
        subject: sujet,
        text: contenu
    }

    transporter.sendMail(mailOptions, (error, info) => {
        if (error) {
            console.error(error)
            res.status(500).send('Erreur lors de l\'envoi de l\'e-mail.')
        } else {
            console.log('E-mail envoyé : ' + info.response)
            res.status(200).send('E-mail envoyé avec succès.')
        }
    })
})
// API ajouter commentaire
app.post('/add-comment', (req, res) => {
    const { userId, rating, text, productId } = req.body
    if (!userId || !rating || !text) {
        return res.status(400).json({ error: 'Missing required fields' })
    }

    const createdAt = new Date().toISOString()

    db.run(
        'INSERT INTO Comment (userId, rating, text, createdAt, productId) VALUES (?, ?, ?, ?, ?)',
        [userId, rating, text, createdAt, productId],
        function (err) {
            if (err) {
                console.error(err)
                return res.status(500).json({ error: 'Failed to add comment' })
            }

            res.status(201).json({ message: 'Comment added successfully' })
        }
    )
})

// API pour obtenir list des commentaires
app.get('/comments/:productId', (req, res) => {
    const productId = req.params.productId
    db.all('SELECT * FROM Comment WHERE productId = ?', [productId], (err, comments) => {
        if (err) {
            console.error(err)
            res.status(500).json({ error: 'Internal server error' })
        } else {
            res.json(comments)
        }
    })
})

app.put('/profil', (req, res) => {
    const updatedUserData = req.body

    console.log('server updateuserdata : ', updatedUserData)

    db.run(
        'UPDATE utilisateur SET  prenom = ?, nom = ?, email = ?, password = ?, no_civique = ?, street = ?, city = ?, pays = ?  WHERE id = ?',
        [

            updatedUserData.prenom,
            updatedUserData.nom,
            updatedUserData.email,
            updatedUserData.password,
            updatedUserData.noCivique,
            updatedUserData.street,
            updatedUserData.city,
            updatedUserData.pays,
            updatedUserData.id
        ],
        (err) => {
            if (err) {
                console.error(err)
                res.status(500).json({ error: 'Erreur du serveur' })
                console.log('server updateuserdata : ', updatedUserData)
            } else {
                res.status(200).json({ message: 'Informations de l\'utilisateur mises à jour avec succès' })
                console.log('server updateuserdata : ', updatedUserData)
            }
        }
    )
})

app.listen(port, () => {
    console.log(`Server is running on port ${port}`)
})
