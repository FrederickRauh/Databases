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
        a {
            color:inherit;
            text-decoration: none;
        }
        .centerBlock{
            margin:0 auto;
        }
        #babbletext{
            width: 50%;
            border-width: 3px;
            border-style: solid;
            border-color:#275193;
            background-color:#eddbad;
        }
        .marked{
            color: red;
        }

        input[name = Edit]  {
        ${editOption}
            background: cornflowerblue;
            color: black;

        }
        input[name = Delete] {
        ${deleteOption}
            background: red;
            color: black;
        }
    </style>
<body>
<div id="wrapper">
    <div id="header">
        <a href="/inserator"><h1 > Inserator Details Website </h1></a>
    </div>

    <div id="inserator_details">
        <fieldset>
        <div id ="titel">${titel}</div>
        <div id ="text">${text}</div>
        <div id ="preis">${preis}</div>
        <div id ="erstellungsdatum">${erstellungsdatum}</div>
        <div id ="ersteller">${ersteller}</div>
            <form action="BenutzerSeiteURL">
                <input name="user_profil" type="hidden" value="$user_profil">
                <input type="submit" value="zuUser_profil" />
            </form>
        </fieldset>



        <div>

            <form  method = "post" action = "/detail">             <!-- /detail ersetzen -->
                <input type="hidden" name ="ID" value = "${idP}"/>
                <input  type="submit" name="Löschen" value ="Löschen" />
                <input  type="submit" name="Kaufen" value ="Kaufen" />
            </form>
            <form method = "post" action = "AnzeigeEditierenURL"
            <input name="user_profil" type="hidden" value="$user_profil">
            <input  type="submit" name="Editieren" value ="Editieren" />

        </div>
    </div>
</body>
</html>
