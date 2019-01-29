<html>
<head>
	<title>Benutzer</title>
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
		<form id="centerBlock" name= "createUser" action="/user" method="post">
			<br/>
			<br/>
			<table class="datatable">
				<tr>
					<th>Vorname: </th>  <th>Nachname: </th> <th>Benutzername: </th> <td>_______________</td> <th>_____________</th>
				</tr>
				<#list users as user>
					<tr>
						<form action="/user" method="post">
							<td>${user.firstname}</td><td>${user.lastname}</td> <td>${user.username}</td>
							<td><button onclick="submit">Nachricht senden</button></td>
							<td><button onclick="submit" name="loadProfil">Profil Besuchen</button></td>
							<td><textarea hidden="true" name="username">${user.username}</textarea></td>
						</form>
					</tr>
				</#list>
			</table>
		</form>
	</div>
</div>
</div>
</body>
</html>