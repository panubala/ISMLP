declare function local:shortestPath($authors, $target, $depth) {
  if ($depth > 100)
  then 'The authors do not have anything in common'
  else
    if (some $author in $authors satisfies $author/id/text() = $target/id/text())
    then $depth
    else local:shortestPath(doc('coAuthors.xml')/root//*[id/text() = $authors//coAuthor/text()], $target, $depth + 1)
};
let $coAuthors := doc('coAuthors.xml')/root//*,
$author := $coAuthors[id = 'Robert B. Sothern'],
$target := $coAuthors[id = 'Vipa Ongwisesphaiboon']
return <root>{
  <item>{
    <shortestPath>{
      local:shortestPath($author, $target, 0)
    }</shortestPath>
  }</item>
}</root>