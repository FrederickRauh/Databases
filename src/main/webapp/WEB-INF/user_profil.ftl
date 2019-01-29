<html>
<head>
    <title>BenutzerProfil</title>
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
    <div id="site">
            <br/>
            <br/>
            <table class="datatable">
                <tr>
                    <td>Vorname:</td>
                    <td>${user.firstname}</td>
                </tr>
                <tr>
                    <td>Nachname:</td>
                    <td>${user.lastname}</td>
                </tr>
                <tr>
                    <td>Benutzername:</td>
                    <td>${user.username}</td>
                </tr>
            </table>
            <#if has>
                <h2>Inserate des Benutzer: </h2>
                <table>
                    <thead>
                    <tr>
                        <th>TimeStamp:</th>
                        <th></th>
                        <th>Titel:</th>
                        <th></th>
                        <th>Status:</th>
                        <th></th>
                        <th>Preis:</th>
                        <th></th>
                    </tr>
                    </thead>
                    <tbody>
                    <#list userAdverts as advert>
                        <form id="advert_detail" name="advert_detail" action="/userProfil" method="post">
                            <tr>
                                <td>${advert.timeStamp}</td>
                                <td> |</td>
                                <td>${advert.title}</td>
                                <td> |</td>
                                <td>${advert.status}</td>
                                <td> |</td>
                                <td>${advert.price}€</td>
                                <td> |</td>
                                <td>
                                    <button onClick="submit">Öffnen</button>
                                </td>
                                <td hidden="true"><textarea name="advertId">${advert.id}</textarea></td>
                            </tr>
                        </form>
                    </#list>
                    </tbody>
                </table>

            </#if>
        <#if cart>
            <h2>Gekaufte Objekte des Benutzers: </h2>
            <table>
                <thead>
                <tr>
                    <th>TimeStamp:</th>
                    <th></th>
                    <th>Titel:</th>
                    <th></th>
                    <th>Status:</th>
                    <th></th>
                    <th>Preis:</th>
                    <th></th>
                </tr>
                </thead>
                <tbody>
                <#list cartAdverts as advert>
                    <form id="advert_detail" name="advert_detail" action="/userProfil" method="post">
                        <tr>
                            <td>${advert.timeStamp}</td>
                            <td> |</td>
                            <td>${advert.title}</td>
                            <td> |</td>
                            <td>${advert.status}</td>
                            <td> |</td>
                            <td>${advert.price}€</td>
                            <td> |</td>
                            <td>
                                <button onClick="submit">Öffnen</button>
                            </td>
                            <td hidden="true"><textarea name="advertId">${advert.id}</textarea></td>
                        </tr>
                    </form>
                </#list>
                </tbody>
            </table>

        </#if>
    </div>
</div>
</div>
</body>
</html>
