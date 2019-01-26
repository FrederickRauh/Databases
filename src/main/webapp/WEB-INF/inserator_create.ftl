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
			<tbody>
			<tr>
				<td><a href="/all"><link>All Inserate</link></a></td>
				<td><p> | </p></td>
				<td><a href="/create"><link>Inserat erstellen</link></a></td>
				<td><p> | </p></td>
				<td><a href="/user"><link>Benutzer</link></a></td>
				<td><p> | </p></td>
				<td><a href="/message"><link>Nachrichten</link></a></td>
			</tr>
			</tbody>
		</table>
	</div>
	<div id="site">
		<form id="centerBlock" name= "createText" action="/create" method="post">
			<table>
				<tbody>
				<tr>
					<td>
						<p>Titel: </p>
					</td>
					<td>
						<textarea name="title" cols="10" rows="1"></textarea>
					</td>
				</tr>
				<tr>
					<td>
						<p>Preis:</p>
					</td>
					<td>
						<textarea name="price" cols="10" rows="1"></textarea>
					</td>
				</tr>
				<tr>
					<td>
						<p>Beschreibung:</p>
					</td>
					<td>
						<textarea name="text" cols="50" rows="6"></textarea>
					</td>
				</tr>
				<tr>
					<br/>
					<td>
						<button onClick="submit" value="Erstellen">Erstellen</button>
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