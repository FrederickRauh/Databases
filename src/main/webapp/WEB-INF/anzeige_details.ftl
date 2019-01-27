<html>
<head>
    <title>Inserator</title>
    <link rel="stylesheet"
          type="text/css"
          href="../css/inserator.css">
</head>
<body>
<div id="wrapper">
    <div id="header">
        <table>
            <tbody id="NavBar">
            <tr>
                <td><a href="/all">
                        <link>
                        All Inserate</link></a></td>
                <td><p> | </p></td>
                <td><a href="/create">
                        <link>
                        Inserat erstellen</link></a></td>
                <td><p> | </p></td>
                <td><a href="/user">
                        <link>
                        Benutzer</link></a></td>
                <td><p> | </p></td>
                <td><a href="/message">
                        <link>
                        Nachrichten</link></a></td>
                <td></td>
                <td></td>
                <td><textarea id="userData" name="userData" cols="10" rows="1"></textarea></td>
            </tr>
            </tbody>
        </table>
    </div>

    <div id="centerBlock">
        <!--<form id="" name= "" action="/details" method="get"> Braucht man hier ein form?-->
        <table>
            <tr>
                <td>
                    ${titel}</td>
            </tr>
            <tr>
                <td>${text}</td>
            </tr>
            <tr>
                <td>${preis}</td>
            </tr>
            <tr>
                <td>${erstellungsdatum}</td>
            </tr>
            <tr>
                <td><a href="/details">
                        <link>
                        Ersteller</link></a></td>
            </tr>


            <form method="post" action="/detail">            <!-- /detail ersetzen? -->
                <!--<input type="hidden" name ="ID" value = ""/> -->
                <tr>
                    <td><input type="submit" name="deleteOption" value="Löschen"/></td>
                    <td><input type="submit" name="buyOption" value="Kaufen"/>

            </form>
            <form method="post" action="/user"

            <td><input type="submit" name="editOption" value="Editieren"/></td>
            </tr>
            </form>
        </table>


        <table>
            <p>
                Kommentare
            </p>
            <!--hier sollen die bisherigen Kommentare (als Liste) geladen werden-->
            <#list comments as comments>
                <tr>
                    <td>${comments.sender}</td> <td>${comments.comments}</td>
                </tr>
            </#list>
            <tr>
                <td>
                    ${comments}</td>
            </tr>

            <form name="createComment" action="/details" method="post">
                <tr>
                    <td><textarea name="createComment" cols="20" rows="10" on></textarea></td>
                </tr>
                <tr>
                    <td><input type="submit" name="addComment" value="Kommentar hinzufügen"/></td>
                </tr>
            </form>
        </table>

    </div>
</div>

</body>
</html>
