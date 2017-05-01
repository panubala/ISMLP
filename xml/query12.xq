let $proceedings := doc('proceedings.xml')/root/*,
$inproceedings := doc('inproceedings.xml')/root/*
return
  <root>{
    for $ip in $inproceedings
    let $p := $proceedings[id/text() = $ip/pid/text()]
    for $author in distinct-values($ip/author/text()[. = $p/editor/text()])
    return
      <author>{
        <id>{$author}</id>
      }</author>
  }</root>