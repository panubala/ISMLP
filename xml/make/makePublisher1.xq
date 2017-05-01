let $root := doc('../proceedings.xml')/root

return <root>{
  
for $proceedings in $root/proceedings,
$publisher in $proceedings//publisher
return <publisher>{
  <id>{$publisher/text()}</id>,
  <pid>{$proceedings/id/text()}</pid>
}</publisher>

}</root>