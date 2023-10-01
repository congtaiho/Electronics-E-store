# E-boutique d'électronique

## Introduction

Cette application a été développé dans le cadre de notre projet final.

Il utilise les notions d'algorithmique et de programmation en [Java] mais également d'autres notions découvertes dans un cadre extra-scolaire.

![The Sac Team App](https://blogger.googleusercontent.com/img/a/AVvXsEhBrTSo5emvSckofMnUOwMl4mOVMLNE7Q5LqaQqXAfSNcSfgAyjg0k9ehTBt84p0cJsCItzInT1AGdZi3Iq6jF8ow9WeCyRlo0vI7Qsq4evlK2d2XHupwPlNC54muGKgpfzIvA12em8n7nsWJQcfBDxo109QrvghNucCCQXDwXTFmKtEG059hPAvsq6szTl=w207-h400 "The Sac Team App")
![The Sac Team App](https://blogger.googleusercontent.com/img/a/AVvXsEiYTFItUEpzCa12OIdk_8RMghYngmvWdKlaTS7jSktIBt_MUOndI8Rhd1mADZ4v6h1m7fgYpJs7tbsPVOzU2Y-AMLKFewmRhPkwfw0oF8oNM9cAJaZksN1zyxxbuv8v4M6o8ltZoY6k9ga_9g1yUKrEr2h7y1e1khXrgqaMnbh_OkPBpTWJ6Fwz2_wxERW8=w208-h400 "The Sac Team App")
![The Sac Team App](https://blogger.googleusercontent.com/img/a/AVvXsEhq66lvYXOS-HY_M3QhM6PzVPWUkRjledPWd1W8iiBDDL9Os3zPzMmlEyFOYYzmUWgVTWRcZFl0h4wy2VOFPgEdmytpd4d8T45lycj--TNDLCsUvL9sbjVlSbzfMKoK1GZ8c9-ZrWfoeQ7sg1GTtgo5ELeO5Mvy88hUwruwU9AbN1xiidAuKY-DzYaLp97S=w206-h400 "The Sac Team App")
![The Sac Team App](https://blogger.googleusercontent.com/img/a/AVvXsEhVXEI6wt0Pu38xYu4_L-Rcvrx5FwXSYEfaQieJ7Y3q11y824nfYSCQ-o4RxUSLaR_vUnKtQb085Mlt-8Tmj5slGXsIsvaidqPC76dfhJLMwqgajoCBOEK1DlxJoum9SZektEMgt6OWCbnBjw9lRrn_f5Gpp2hVYTRZgGGRRsGp7qezaZoiwZ7Sw6acKCtG=w207-h400 "The Sac Team App")

## Description

> Cette application est une plateforme de vente en ligne spécialisée dans les produits électroniques et technologiques. Notre application propose une gamme variée de produits électroniques de haute qualité, allant des smartphones et des ordinateurs portables aux gadgets et aux accessoires électroniques.

## Fonctionnalités

Il existe plusieurs fonctionnalités parmi ces fonctionnalités :

- Affiche la liste de produits avec la possibilité de filtrer les produits par catégorie.
- Affiche les détails d'un produit, y compris son nom, sa description, son prix et une image.
- Affiche les détails de la commande, y compris le nom du produit, le prix, la quantité, le sous-total, la TVQ, la TPS et le total TTC.
- Affiche le contenu du panier de l'utilisateur.
- Permet à l'utilisateur de supprimer des produits de son panier.
- Affiche le montant total des produits dans le panier.
- Permet à l'utilisateur de se connecter ou de s'inscrire.
- Affiche l'historique des commandes passées par l'utilisateur.
- Permet à l'utilisateur de mettre à jour ses informations de profil.
- Permet à l'utilisateur de saisir des informations de paiement (prénom, nom, numéro de carte, date d'expiration, CVV).
- Affiche un message de remerciement à l'utilisateur après avoir passé une commande.
- Envoie un e-mail de confirmation de commande à l'utilisateur.
- Push notifications (OneSignal).

## Préparation et Installation

#### Installation du logiciel

Vous devez télécharger Android Studio (http://developer.android.com/sdk/index.html), qui installera également le gestionnaire de SDK.

#### SDK Manager

L'installation devrait automatiquement télécharger tous les composants nécessaires. Si vous effectuez une configuration manuelle, téléchargez au moins les composants suivants à l'aide du gestionnaire de SDK : Dans le dossier "outils" :

- SDK Tools
- SDK Platform Tools
- SDK Build Tools

Et au moins une version du SDK (au moins Android 5.0). Une fois que vous avez terminé, vous pouvez ouvrir Android Studio et poursuivre votre développement.

#### Importer le projet

- 1 Démarrez Android Studio et fermez tous les projets Android Studio ouverts.
- 2 Dans le menu Android Studio, sélectionnez Fichier > Nouveau > Importer le projet.
- 3 Sélectionnez l'emplacement où vous avez placé vos fichiers de projet.
- 4 Appuyez sur OK et attendez que tous les processus se terminent.

![Importer le projet](https://blogger.googleusercontent.com/img/b/R29vZ2xl/AVvXsEjS0Z20Geh7-itPBg3eweBU6q9G3e8zzDJec5duLNS4HqyG0ftmJUralXIoVPPID0F6Zz79Dc16pVNHZNV4YR9TnqAel186P8jKpaAGbJMtQldh0N4m6O9pgWAuCdiw7YseWHicK4dNffA4Nmhg2HAnEMgGXmVIOQbFgFbO34TCqC-YP71mbfTcLdb3myVW/s16000/import.png "Importer le projet")

- 5 Pour démarrer l'application, vous devez cliquer sur la flèche verte, comme mentionné dans l'image ci-dessous.

![Démarrer l'application](https://blogger.googleusercontent.com/img/b/R29vZ2xl/AVvXsEjMHLhjTV947UX4uvnonjBIqPdl4qKSywXUtIqY-nHC3wq8m90WQQyiEqJr8CHv4HnN_eGWN8zTUPfG-oXoLK5tMUHuJrj-zJJV5giGvV4BXSFnXo-lIpa1kBg86CZkQqZYK1arDgAFtxz-eZ81_QUMnqBaxOmhqOdxZeyDYRvpBsows4EKt2RAAcPgYAFv/s16000/run.png "Démarrer l'application")

Parfois, Gradle Build échoue. Voici quelques étapes supplémentaires que vous pouvez suivre : Android Studio pourrait vous demander de télécharger certains composants SDK supplémentaires dans l'onglet 'messages', suivez simplement les instructions pour les installer et télécharger les composants supplémentaires afin de résoudre ce problème.

## Crédits

Dernière mise à jour le 09/18/2023

------------------

[@Taoufik Boussemousse]
[@Antoine Ouellet]
[@Evan Cholette]
[@Sara Salek]
[@Công Tai Hô]
[@Charles-Maximilien Gros]
