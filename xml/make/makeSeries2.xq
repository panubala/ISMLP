let $seriess := doc('../series.xml')/root//series
return <root>{
  
for $series in $seriess
group by $seriesId := $series/id
let $publications := $series//pid
return <series>{
  <id>{$seriesId}</id>,
  for $publication in $publications
  return $publication
}</series>

}</root>