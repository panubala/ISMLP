let $proceedings := doc('proceedings.xml')/root/*[1982 <= ceid and ceid <= 1986],
$inproceedings := doc('inproceedings.xml')/root/*,
$publishers :=
  for $p in $proceedings
  where every $e in $p/editor satisfies $e/id/text() = $inproceedings/author/id/text()
  return $p/publisher
return <root>{
  for $publisher in distinct-values($publishers)
  return <publisher><id>{$publisher}</id></publisher>
}</root>