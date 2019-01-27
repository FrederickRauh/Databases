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
		<h2>Nachrichten für Sie</h2>
		<table>
			<thead>
			<tr>
				<th>Von: </th>
				<th>Nachricht: </th>
			</tr>
			</thead>
			<tbody>
			<#list messagesIn as message>
				<tr>
					<td>${message.sender}</td> <td>${message.message}</td>
				</tr>
			</#list>
			</tbody>
		</table>
		<h2>Nachrichten von Ihnen</h2>
		<table>
			<thead>
			<tr>
				<th>An: </th>
				<th>Nachricht: </th>
			</tr>
			</thead>
			<tbody>
			<#list messagesOut as message>
				<tr>
					<td>${message.receiver}</td> <td>${message.message}</td>
				</tr>
			</#list>
			</tbody>
		</table>
	</div>
</div>
</div>
</body>
</html>