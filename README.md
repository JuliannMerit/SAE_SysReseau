
# SAE 3.03 - Système et Réseaux
## *Réseau social Tuit'o*
![GitHub release (latest by date)](https://img.shields.io/github/v/release/Thomas-Webber/SAE-3.03?style=for-the-badge)

![GitHub last commit](https://img.shields.io/github/last-commit/Thomas-Webber/SAE-3.03?style=for-the-badge)

![GitHub issues](https://img.shields.io/github/issues/Thomas-Webber/SAE-3.03?style=for-the-badge)
### [Sujet](sujetSAE303.pdf)

## Description du projet
Le projet consiste à créer une application client-serveur d'un réseau social nommé `Tuit'o`.

## Membres du groupe
- `MERIT Juliann`
- `AVIGNON Théo` 

## Fonctionnalités
- [ ] Fonctionner en ligne de commande
- [ ] Interface graphique

### Client :
- [ ] Création d'un compte
- [ ] Connexion à un compte
- [ ] Voir les messages des personnes auxquelles il est abonné
- - [ ] Dans l'ordre chronologique
- [ ] Détail d'un message
- - [ ] Auteur
- - [ ] Date et heure de publication
- - [ ] Contenu
- - [ ] Nombre de likes
- - [ ] Identifiant du message
- [ ] Création d'un message
- - [ ] Envoyé à ses abonnés (sans action de sa part)
- [ ] Effectuer des commandes :
- - [ ] `/follow <nom utilisateur>` : permet de s’abonner à un nouvel utilisateur. Si celui-ci n’existe pas un message d’erreur s’affiche.
- - [ ] `/unfollow <nom utilisateur>` : permet de se désabonner d’un utilisateur.
- - [ ] `/like <id message>` : permet de liker un message.
- - [ ] `/delete <id message>` : permet de supprimer un de ses messages.

### Serveur :
- [ ] Accepter les connexions de nouveaux clients
- [ ] Renvoyer les messages des personnes auxquelles le client est abonné
- [ ] Gérer plusieurs messages simultanément
- [ ] Effectuer les commandes :
- - [ ] `/delete <id_message>` : permet de supprime un message.
- - [ ] `/remove <nom_utilisateur>` : pert de supprime un utilisateur, ainsi que tous ses messages.

### Messages :
Le format doit être en JSON, et contenir les informations suivantes :
```json
{
    "id": 1,
    "user": "toto",
    "content": "Hello World !",
    "date": "2024-01-20T09:00:00Z",
    "likes": 0
}
```

