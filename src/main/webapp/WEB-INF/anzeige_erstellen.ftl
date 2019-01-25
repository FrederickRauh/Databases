<html>
<head>
    <title>Inserator</title>
    <link rel="stylesheet" href="inserator_create.css">
</head>
<body>
<div clasName="wrapper">
    <div className="header">
        <a href ="/inserator"><h1> Anzeige erstellen </h1></a>
    </div>
    <div className="site">
       <form className="centerBlock" name= "createText" action="/create" method="post">
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
