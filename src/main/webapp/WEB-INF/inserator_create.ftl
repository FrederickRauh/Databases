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
		<form id="centerBlock" name= "createText" action="/create" method="post">
			<table>
				<tbody>
				<tr>
					<td><p>Titel: </p></td>
					<td><textarea name="title" cols="20" rows="1"></textarea></td>
				</tr>
				<tr>
					<td><p>Preis:</p></td>
					<td><textarea name="price" cols="20" rows="1"></textarea></td>
				</tr>
				<tr>
					<td><p>Beschreibung:</p></td>
					<td><textarea name="text" cols="50" rows="6"></textarea></td>
				</tr>
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
					<br/>
					<td>
						<button onClick="submit" value="create">Erstellen</button>
					</td>
				</tr>
				</tbody>
			</table>
		</form>
	</div>
</div>
</div>
</body>
</html>