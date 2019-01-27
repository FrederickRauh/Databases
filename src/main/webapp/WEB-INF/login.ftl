<html>
<head>
    <title>Login</title>
    <link rel="stylesheet"
          type="text/css"
          href="../css/login.css">
</Head>
<body>
<div id="wrapper">
    <div id="header">
        <table>
            <tr>
                <td><a href="/login">LOGIN</a></td>
            </tr>
        </table>
    </div>
    <div id="Login">
        <form id="LoginBlock" name="createUser" action="/login" method="post">
            <table>
                <thead>
                <tr>
                    <th><p>Name: </p></th>
                    <th><p>Username: </p></th>
                    <th><p>New? </p></th>
                </tr>
                </thead>
                <tbody>
                <tr>
                    <td><textarea name="name"></textarea></td>
                    <td><textarea name="username"></textarea></td>
                    <td><input type="checkbox" name="register"/></td>
                    <td>
                        <button onClick="submit" value="login">Login</button>
                    </td>
                </tr>
                </tbody>
            </table>
            <br/>
            <br/>
        </form>
    </div>
</div>
</body>
</html>
