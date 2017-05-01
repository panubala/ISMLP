let $proceedings := doc('proceedings.xml')/root//*[cid/text() = 'CONPAR'],
$inproceedings := doc('inproceedings.xml')/root//*[pid/text() = $proceedings/id/text()]
return
  <root>{
    for $author in distinct-values($proceedings/editor/text() | $inproceedings/author/text())
    return
      <author>{
        <id>{$author}</id>
      }</author>
  }</root>