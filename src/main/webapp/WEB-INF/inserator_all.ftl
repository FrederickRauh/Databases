<html>
<head>
    <title>Alle Inserate</title>
    <link rel="stylesheet"
          type="text/css"
          href="../css/inserator.css">
</Head>
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
    <div id="site">
        <div id="centerBlock">
            <table class="datatable">
                <tr>
                    <th>Title</th>
                    <th>Preis</th>
                    <th>Text</th>
                </tr>
                <form id="advert_detail" name="advert_detail" action="/all" method="post">
                    <#list adverts as advert>
                        <tr>
                            <td>${advert.title}</td>
                            <td>${advert.price}</td>
                            <td>${advert.text}</td>
                            <td>
                                <button name="id" onClick="submit" value=${advert.id}>Öffnen</button>
                            </td>
                        </tr>
                    </#list>
                </form>
            </table>
        </div>
    </div>
</div>
</body>
</html>
