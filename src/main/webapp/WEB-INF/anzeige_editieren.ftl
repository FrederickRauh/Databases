
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
    </style>

<body>
<div id="wrapper">
    <div id="header">
        <a href ="/inserator"><h1> Anzeige erstellen </h1></a>
    </div>

    <div id="site">

        <form name="createTitle" method = "post" action = "/createTitle">

            <p>Titel:</p>
            <textarea Name = "createTitle" cols="50" rows="1"></textarea>
        </form>


        <p>Kategorie:</p>
        <div>
            <input type="checkbox" name="Digitale Waren" value = "Digitale Waren"/><label>Digitale Waren</label>
        </div>
        <div>
            <input type="checkbox" name="Haus & Garten" value = "Haus & Garten"/><label>Haus & Garten</label>
        </div>
        <div>
            <input type="checkbox" name="Mode & Kosmetik" value = "Mode & Kosmetik"/><label>Mode & Kosmetik</label>
        </div>
        <div>
            <input type="checkbox" name="Multimedia & Elektronik" value = "Multimedia & Elektronik"/><label>Multimedia & Elektronik</label>
        </div>

        <form name = "createText" method="post" action="/createPrice">
            <p>Preis:</p>
            <textarea preis = "createPrice" cols="10" rows="1"></textarea>
            <p>Beschreibung:</p>
            <textarea text = "createText" cols="50" rows="6"></textarea>
            <input type ="submit" value="Aktualisieren"/>
        </form>


    </div>
</div>
</body>
</html>
