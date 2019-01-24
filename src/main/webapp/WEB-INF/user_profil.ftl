<html>
<head><title>Inserator</title>
    <style type="text/css">
        * {
            margin:0;
            padding:0;
        }

        body{
            text-align:center;
            background: #efe4bf none repeat scroll 0 0;
        }

        #wrapper{
            width:960px;
            margin:0 auto;
            text-align:left;
            background-color: #fff;
            border-radius: 0 0 10px 10px;
            padding: 20px;
            box-shadow: 1px -2px 14px rgba(0, 0, 0, 0.4);
        }

        #header{
            color: #fff;
            background-color: #2c5b9c;
            height: 3.5em;
            padding: 1em 0em 1em 1em;

        }

        #site{
            background-color: #fff;
            padding: 20px 0px 0px 0px;
        }
        .centerBlock{
            margin:0 auto;
        }
        a {
            color:inherit;
            text-decoration: none;
        }

        }
        #Gekauft div{
            width: 50%;
            border-width: 3px;
            border-style: solid;
            border-color:#275193;
            background-color:#eddbad;
        }

    </style>
<body>
<div id="wrapper">
    <div id="header">
        <a href="/inserator"><h1 > Benutzerprofil </h1></a>
        </div>
            </div>

        <div id="site">
        <div id ="Benutzerinformationen">
            <p>Benutzername: ${benutzername}</p>
            <p>Name: ${name}</p>
            <p>Eintrittsdatum: ${eintrittsdatum}</p>
            <p>Anzahl verkaufter Artikel: ${anzahlVerkaufterArtikel}</p>
            <p>Status: ${status}</p>
        </div>

        <div id="Angebote">${angebote}</div>
        <p>Titel: ${titel}</p>
        <p>Erstellungsdatum: ${erstellungsdatum}</p>
        <p>Preis: ${preis}</p>
        <p>Status: ${status}</p>

        <div id = "Gekauft">${gekaufteArtikel}</div>
    </div>


</body>
</html>
