</body>
<script>
	function renderPage(){
		if( typeof initPage != 'undefined' && $.isFunction(initPage)){
			initPage();
		}
	}
	setTimeout("renderPage()",0);
	
</script>
</html>