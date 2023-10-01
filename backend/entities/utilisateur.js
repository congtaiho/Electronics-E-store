import Panier from './Panier'

class Utilisateur {
    constructor (id, nom, prenom, email, password, noCivique, street, city, pays, imageProfil) {
        this.id = id
        this.nom = nom
        this.prenom = prenom
        this.email = email
        this.password = password
        this.noCivique = noCivique
        this.street = street
        this.city = city
        this.pays = pays
        this.imageProfil = imageProfil
        this.panier = new Panier()
        this.session = false
    }
}

export default Utilisateur
