
# SAE 3.03 - Système et Réseaux
## *Réseau social Tuit'o*

## Description du projet
Le projet consiste à créer une application client-serveur d'un réseau social nommé `Tuit'o`.

### Client :
Le client dispose des commandes suivantes :

- `/connect <pseudo> : permet de se connecter au serveur avec le pseudo <pseudo>`.
- `/follow <pseudo> : permet de suivre l'utilisateur <pseudo>`.
- `/unfollow <pseudo> : permet de ne plus suivre l'utilisateur <pseudo>`.
- `/post <message> : permet d'envoyer un message <message> à tous les utilisateurs qui le suivent`.
- `/like <id> : permet de liker le message <id>`.
- `/unlike <id> : permet de ne plus liker le message <id>`.
- `/delete <id> : permet de supprimer le message <id>`.

### Serveur :

Le serveur dispose des commandes suivantes :

- `/delete <id> : permet de supprimer le message <id>`.
- `/remove <pseudo> : permet de supprimer l'utilisateur <pseudo>`.
- `/exit : permet de fermer le serveur`.

## Architecture du projet

- `Client` : permet de creer une session et d'y lier un User.
- `Server` : permet de gérer les connexions des clients et de recevoir les messages.
- `Message` : gère les messages et ce qui les composent.
- `User` : gère les utilisateurs et ce qui les composent.
- `Session` : fait le lien entre le client et le serveur.
- `ThreadServer` : permet de gérer les commandes du serveur.

Nous avons utilsé cette architecture car elle était la plus simpliste et la plus facile à implémenter et réaliser, bien que certaines architectures plus complexes auraient pu être utilisées pour approffondir le projet et ajouter des fonctionnalités.

Nous n'avons malheureusement pas eu le temps de réaliser plus de fonctionnalités, ni l'interface graphique, car nous étions occupés par d'autres projets, ainsi que par les diffrents examens.