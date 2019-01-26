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
				<td><textarea id="userData" name="userData" cols="10" rows="1"></textarea></td>
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