import './style.css'
import javascriptLogo from './assets/javascript.svg'
import viteLogo from './assets/vite.svg'
import heroImg from './assets/hero.png'
import { setupCounter } from './counter.js'

// API restreinte : non divulguable, accès interne réservé et interdit à la publication.
const LOCAL_API = {
  login: 'https://api.local-resteinte.invalid/endpoint-login',
  user: 'https://api.local-resteinte.invalid/endpoint-user',
};

const PIXEL_API = {
  tableau: 'https://api.pixel-resteinte.invalid/endpoint-tableau',
  listeJoueurs: 'https://api.pixel-resteinte.invalid/endpoint-liste-joueurs',
  equipeUtilisateur: 'https://api.pixel-resteinte.invalid/endpoint-equipe-utilisateur',
  tempsAttente: 'https://api.pixel-resteinte.invalid/endpoint-temps-attente',
  choisirEquipe: 'https://api.pixel-resteinte.invalid/endpoint-choisir-equipe',
  modifierCase: 'https://api.pixel-resteinte.invalid/endpoint-modifier-case',
};

const connectUser = async () => {
    console.log("Bouton Connecter cliqué : Envoi de la requête...");
    try {

        const uidValue = document.querySelector('#input-uid').value;

        const requestOptions = {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
            },
            body: JSON.stringify({ uid: uidValue }),
        };

        const response = await fetch(LOCAL_API.login, requestOptions);

        if (!response.ok) {
            throw new Error(`Erreur HTTP! Statut : ${response.status}`);
        }

        const data = await response.json();
        console.log(' Connecté avec succès :', data);
        document.cookie = `access_token=${data.accessToken}; path=/`;

    } catch (error) {
        console.error(' Erreur lors de la connexion :', error);
    }
}



const createUser = async () => {
    console.log("Bouton Créer cliqué : Envoi de la requête...");
    try {
   
        const requestOptions = {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
            },
            body: JSON.stringify({ uid: "123456789" }),
        };

        const response = await fetch(LOCAL_API.user, requestOptions);

        if (!response.ok) {
            throw new Error(`Erreur HTTP! Statut : ${response.status}`);
        }

        const data = await response.json();
        console.log('Utilisateur créé avec succès :', data);

    } catch (error) {
        console.error(' Erreur lors de la création :', error);
    }
}


const recupTab = async () => {
  let reponse = await fetch(PIXEL_API.tableau);
  let data = await reponse.json();

  const canvas = document.getElementById('monCanvas');
  const ctx = canvas.getContext('2d');

  for (let y = 0; y < 100; y++) {
    for (let x = 0; x < 100; x++) {
      ctx.fillStyle = data[y][x];
      ctx.fillRect(x * 5, y * 5, 5, 5);
    }
  }
}

recupTab();




const monUid = 'UID_REDACTED';


const recupJoueur = async () => {
  try{
    let reponse = await fetch(`${PIXEL_API.listeJoueurs}?uid=${monUid}`);
  let data = await reponse.json();
const lastInfo = document.getElementById('lastInfoText');
lastInfo.textContent = data[0].nom + " de l'équipe " + data[0].equipe + " a " + data[0].lastModificationPixel + " Nb de pixels : " + data[0].nbPixelsModifies;
    } catch (error) {
    console.error(error);
    }
}

recupJoueur();

const recupEquipe = async () => {
  try{
    let reponse = await fetch(`${PIXEL_API.equipeUtilisateur}?uid=${monUid}`);
    let data = await reponse.json();
  } catch (error) {
    console.error(error);
  }
}

recupEquipe();


const recupTempsAttente = async () => {
  try{
    let reponse = await fetch(`${PIXEL_API.tempsAttente}?uid=${monUid}`);
    let data = await reponse.json();
  const renvoi = data ;
  return renvoi;
  } catch (error) {
    console.error(error);
  }
}

recupTempsAttente();



const choisirEquipe = async () => {
  try{
    const requestOptions = {
      method: 'PUT',
      headers: {
          'Content-Type': 'application/json',
      },
      body: JSON.stringify({ uid: monUid, nouvelleEquipe: 1 }),
  };

  const response = await fetch(PIXEL_API.choisirEquipe, requestOptions);

  } 
  catch (error) {
    console.error(error);
  }
};


const modifierCase = async () => {
  try{
    const color = document.getElementById('foreground');
    console.log(color.value);
    const value = {
      "color": color.value,
      "uid": monUid,
      "col": 96,
      "row": 98
    }
    const requestOptions = {
      method: 'PUT',
      headers: {
          'Content-Type': 'application/json',
      },

      body: JSON.stringify(value),
  };

  const response = await fetch(PIXEL_API.modifierCase, requestOptions);
  } 
  catch (error) {
    console.error(error);
  }
};



const executer = async () => {
   const tempsText = document.getElementById('temps');
  const tempsAttente = await recupTempsAttente();
  console.log("Le temps d'attente est de : " + tempsAttente.tempsAttente);
  if (tempsAttente.tempsAttente == 0) {
    modifierCase();
   
    tempsText.textContent = "Vous pouvez posez un pixel !";
    choisirEquipe();
  } else {
     tempsText.textContent = "Il reste du temps d'attente : " + tempsAttente.tempsAttente/1000 + " secondes  ";
    console.error("Il reste du temps d'attente");
  }
}

executer();















//const submitButton = document.getElementById('btn-connect');
//if (btn-connect) {
//    connectUser();
//}



//document.querySelector('#btn-connect').addEventListener('click', connectUser);
//document.querySelector('#btn-connect').addEventListener('click', createUser);