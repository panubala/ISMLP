let $persons := doc('../persons.xml')/root//person
return <root>{
  
for $person in $persons
group by $personId := $person/id
let $publications := $person//(iid | pid)
return <person>{
  <id>{$personId}</id>,
  for $publication in $publications
  return $publication
}</person>

}</root>