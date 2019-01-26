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
            <tbody>
            <tr>
                <td><a href="/all"><link>All Inserate</link></a></td>
                <td><p> | </p></td>
                <td><a href="/create"><link>Inserat erstellen</link></a></td>
                <td><p> | </p></td>
                <td><a href="/user"><link>Benutzer</link></a></td>
            </tr>
            </tbody>
        </table>
    </div>
    <div id="site">
        <div id="centerBlock">
            <table class="datatable">
                <tr>
                    <th>Title</th>  <th>Preis</th> <th>Text</th>
                </tr>
                <#list anzeigen as anzeige>
                    <tr>
                        <td>${anzeige.title}</td> <td>${anzeige.price}</td> <td>${anzeige.text}</td>
                    </tr>
                </#list>
            </table>
        </div>
    </div>
</div>
</body>
</html>
