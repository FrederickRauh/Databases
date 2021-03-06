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
            </tr>
            </tbody>
        </table>
    </div>

    <div id="centerBlock">
        <table>
            <tr>
                <td>Titel: ${advert.title}</td>
            </tr>
            <tr>
                <td>Preis: ${advert.price}€</td>
            </tr>
            <tr>
                <td>Status: ${advert.status}</td>
            </tr>
        </table>
        <textarea contenteditable="false" rows="10" cols="134">${advert.text}</textarea>
        <table>
            <tr>
                <td>Von: ${advert.creator}</td>
                <td> |</td>
                <td>${advert.timeStamp}</td>
            </tr>
        </table>
        <table>
            <tr>
                <td><form action="/detail" method="post"><button onClick="submit" name="profil">Zum Profil des verkäufers</button></form></td>
                <#if advert.status != 'verkauft'>
                    <#if !isCreator><td><form action="/detail" method="post"><button onClick="submit" name="buy">Kaufen</button></form></td></#if>
                    <#if isCreator>
                        <td><form action="/detail" method="post"><button onClick="submit" name="edit">Editieren</button></form></td>
                        <td><form action="/detail" method="post"><button onClick="submit" name="delete">Löschen</button></form></td>
                    </#if>
                    <#else>
                    <p id="verkauft">Bereits VERKAUFT an ${buyer} am ${buyTime}</p>
                </#if>

            </tr>
        </table>
        <p>Kommentare: </p>
        <table>
            <thead>
            <th>Sender:</th>
            <th>Text:</th>
            </thead>
            <tbody>
            <#list comments as comment>
                <tr>
                    <td>${comment.username}</td>
                    <td> |</td>
                    <td>${comment.text}</td>
                </tr>
            </#list>
            </tbody>
        </table>
        <form action="/detail" method="post">
            <table>
                <tr>
                    <td><textarea name="text" rows="5" cols="50"></textarea></td>
                    <td>
                        <button onClick="submit" name="comment">Kommentieren</button>
                    </td>
                </tr>
            </table>
        </form>

    </div>
</div>

</body>
</html>
