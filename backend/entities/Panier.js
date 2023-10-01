class Panier {
    constructor () {
        this.articles = [] // Tableau des articles dans le panier
        this.total = 0 // Total du panier
        this.dateCreation = new Date() // Date de création du panier
        this.dateMiseAJour = new Date() // Date de la dernière mise à jour du panier
    }

    // Ajouter un article au panier
    ajouterArticle (article) {
        this.articles.push(article)
        this.mettreAJourTotal()
        this.mettreAJourDate()
    }

    // Supprimer un article du panier
    supprimerArticle (article) {
        const index = this.articles.findIndex(item => item.id === article.id)
        if (index !== -1) {
            this.articles.splice(index, 1)
            this.mettreAJourTotal()
            this.mettreAJourDate()
        }
    }

    // Mettre à jour le total du panier
    mettreAJourTotal () {
        this.total = this.articles.reduce((acc, article) => acc + article.prix, 0)
    }

    // Mettre à jour la date de mise à jour du panier
    mettreAJourDate () {
        this.dateMiseAJour = new Date()
    }
}

export default Panier
