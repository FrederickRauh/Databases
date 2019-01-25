<html>
<head><title>Alle Inserate</title>
   </Head>
<body>
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
</body>
</html>
