let $publishers := doc('../publishers.xml')/root//publisher
return <root>{
  
for $publisher in $publishers
group by $publisherId := $publisher/id
let $publications := $publisher//pid
return <publisher>{
  <id>{$publisherId}</id>,
  for $publication in $publications
  return $publication
}</publisher>

}</root>