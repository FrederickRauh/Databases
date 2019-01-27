<html>
<head>
	<title>Editieren</title>
	<link rel="stylesheet"
		  type="text/css"
		  href="../css/inserator.css">
</head>
<body>
<div id="wrapper">
	<div id="header">
		<table>
			<tbody id = "NavBar">
			<tr>
				<td><a href="/all"><link>All Inserate</link></a></td>
				<td><p> | </p></td>
				<td><a href="/create"><link>Inserat erstellen</link></a></td>
				<td><p> | </p></td>
				<td><a href="/user"><link>Benutzer</link></a></td>
				<td><p> | </p></td>
				<td><a href="/message"><link>Nachrichten</link></a></td>
				<td></td>
				<td></td>
			</tr>
			</tbody>
		</table>
	</div>
	<div id="site">
		<form action="/edit" method="post">
			<table>
				<tr>
					<td>Titel: </td>
					<td><textarea name="title">${advert.title}</textarea></td>
				</tr>
				<tr>
					<td>Preis: </td>
					<td><textarea name="price">${advert.price}</textarea></td>
				</tr>
				<tr>
					<td>Text: </td>
					<td><textarea name="text">${advert.text}</textarea></td>
				</tr>
			</table>
			<button onclick="submit">Editieren</button>
		</form>

	</div>
</div>
</div>
</body>
</html>