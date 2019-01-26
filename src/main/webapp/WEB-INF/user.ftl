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
		<form id="centerBlock" name= "createUser" action="/user" method="post">
			<table>
				<thead>
				<tr>
					<th><p>Vornamen: </p></th>
					<th><p>Nachnamen:</p></th>
				</tr>
				</thead>
				<tbody>
				<tr>
					<td><textarea name="firstName"></textarea></td>
					<td><textarea name="lastName"></textarea></td>
					<td><button onClick="submit" value="Erstellen">Hinzufügen</button></td>
				</tr>
				</tbody>
			</table>
			<table class="datatable">
				<tr>
					<th>Vorname: </th>  <th>Nachname: </th> <th>Benutzername: </th>
				</tr>
				<#list users as user>
					<tr>
						<td>${user.firstname}</td><td>${user.lastname}</td> <td>${user.username}</td>
					</tr>
				</#list>
			</table>
		</form>
	</div>
</div>
</div>
</body>
</html>