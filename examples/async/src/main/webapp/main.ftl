<HTML>
<BODY>

    <div id="customers">
        <#list customers as customer>
            Customer Name: ${customer.name} Email: ${customer.email}<br>
        </#list>
    </div>
    <br><br>

    <div id="books">
            <#list books as book>
                Book Title: ${book.title} Author: ${book.author}<br>
            </#list>
    </div>

</BODY>
</HTML>