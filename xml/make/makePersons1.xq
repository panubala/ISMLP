let $inproceedings := doc('../inproceedings.xml')/root//inproceedings,
$proceedings := doc('../proceedings.xml')/root//proceedings
return <root>{
  
for $publication in $inproceedings,
$author in $publication//author
return <person>{
  <id>{$author/text()}</id>,
  <iid>{$publication/id/text()}</iid>
}</person>,
  
for $publication in $proceedings,
$editor in $publication//editor
return <person>{
  <id>{$editor/text()}</id>,
  <pid>{$publication/id/text()}</pid>
}</person>

}</root>