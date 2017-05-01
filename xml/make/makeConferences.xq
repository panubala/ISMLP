let $ces := doc('../conferenceEditions.xml')/root//conferenceEdition

return <root>{

for $ce in $ces
group by $cid := $ce/cid
return <conference>{
  <id>{$cid}</id>,
  for $ce in $ces[cid = $cid]
  return <ceid>{$ce/id/text()}</ceid>
}</conference>

}</root>