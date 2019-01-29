<html>
<head>
	<title>Nachrichten</title>
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
		<#if hasMessages>
			<h2>Chatverlauf</h2>
			<table>
				<thead>
				<tr>
					<th>Von: </th>
					<th> | </th>
					<th>Nachricht: </th>
				</tr>
				</thead>
				<tbody>
				<#list msgs as message>
					<tr>
						<td>${message.sender}</td><td> | </td><td>${message.message}</td>
					</tr>
				</#list>
				</tbody>
			</table>
		</#if>

		<p>Nachricht an: ${toUser} </p>
		<form action="/send" method="post">
		<table>
			<tr>
				<td><textarea name="message" cols="50" rows="10"></textarea></td>
				<td><button onclick="submit">Versenden</button></td>
			</tr>
		</table>
		</form>

	</div>
</div>
</div>
</body>
</html>