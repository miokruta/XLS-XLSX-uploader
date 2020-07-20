<%@page session="false"%>
<html>
<body>
<h1>hi</h1>
<c:url value="/uploadExcelFile" var="uploadFileUrl" />
<form method="post" enctype="multipart/form-data"
      action="${uploadFileUrl}">
    <input type="file" name="file" accept=".xls,.xlsx" /> <input
        type="submit" value="Upload file" />
</form>
</body>
</html>