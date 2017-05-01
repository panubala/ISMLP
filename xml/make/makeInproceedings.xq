let $root := doc('../database.xml')/root
return <root>{
  
for $inproceedings in $root//inproceedings
return <inproceedings>{
  <id>{data($inproceedings/@key)}</id>,
  <pid>{$inproceedings/crossref/text()}</pid>,
  <title>{$inproceedings/title/text()}</title>,
  for $author in $inproceedings//author
  return <author>{$author/text()}</author>
}</inproceedings>

}</root>