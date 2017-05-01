let $root := doc('../proceedings.xml')/root

return <root>{
  
for $proceedings in $root/proceedings,
$series in $proceedings//sid
return <series>{
  <id>{$series/text()}</id>,
  <pid>{$proceedings/id/text()}</pid>
}</series>

}</root>