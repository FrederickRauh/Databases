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
            </tr>
            </tbody>
        </table>
    </div>
    <div id="site">
        <div id="centerBlock">
            <form id="LoginBlock" name="filter" action="/all" method="get">
            <h2>Nach Kategorie filter? </h2>
            <table>
                <tr>
                    <td>Digitale Waren: </td><td><input type="checkbox" name="digital"/></td>
                </tr>
                <tr>
                    <td>Haus & Garten: </td><td><input type="checkbox" name="house"/></td>
                </tr>
                <tr>
                    <td>Mode & Kosmetik</td><td><input type="checkbox" name="fashion"/></td>
                </tr>
                <tr>
                    <td>Multimedia & Elektronik</td><td><input type="checkbox" name="electronic"/></td>
                </tr>
                <tr>
                    <td><button onclick="submit">Filtern</button></td>
                </tr>
            </table>
            </form>

            <h2>INSERATE</h2>

            <table class="datatable">
                <tr>
                    <th>Title</th>
                    <th>Preis</th>
                    <th>Text</th>
                </tr>
                <#list adverts as advert>
                    <form id="advert_detail" name="advert_detail" action="/detail" method="get">
                        <tr>
                            <td>${advert.title}</td>
                            <td>${advert.price}€</td>
                            <td>${advert.text}</td>
                            <td hidden="true"><textarea name="id">${advert.id}</textarea></td>
                            <td>
                                <button onClick="submit">Öffnen</button>
                            </td>
                        </tr>
                        <tr>
                            <td>----------------</td>
                            <td>----</td>
                            <td>-----------------------------------------------------------------------------------------------------------------------------</td>
                        </tr>
                    </form>
                </#list>
            </table>
        </div>
    </div>
</div>
</body>
</html>
