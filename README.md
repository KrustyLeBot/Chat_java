# Chat_java

-L'envoi des messages entre les utilisateurs via une IHM est réalisé.

-Quand un utilisateur se connecte il est automatiquement ajouté à la hashmap de tous les autres utilisateurs et il devient visible et sélectionnable via l'interface.

-La deconnexion est fonctionnelle, un utilisateur déconnecté est bien supprimé de la hashmap des autres utilisateurs.

-Le changement de pseudo est réalisable sans déconnexion, directement via l'interface.

-Les messages des conversations sont sauvegardés en local, tout utilisateur possède une sauvegarde de ses conversations.

-Nous avons mis en place le serveur via un autre projet (numerosbis) dans le programme principal il faut renseigner l'addresse ip du serveur pour pouvoir s'y connecter. Les changements d'états sont alors récupérés par le serveur qui met sa table à jour, affiche le changement dans son interface puis renvoie sa table à tous les utilisateurs. Nous n'avons pas eu le temps de mettre en place l'affichage dans l'IHM des changements d'état des utilisateurs, mais cela est bien géré en backend. 

-Vous trouverez une capture d'écran montrant les interfaces du serveur et du chat, dans la configuration où deux utilisateurs sont connectés dont un étant sur la même machine que le serveur.