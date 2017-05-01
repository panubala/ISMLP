let $proceedings := doc('proceedings.xml')/root//*[cid/text() = 'CONPAR'],
$inproceedings := doc('inproceedings.xml')/root//*[pid/text() = $proceedings/id/text()]
return <root><item><count>{count(distinct-values($proceedings/editor/text() | $inproceedings/author/text()))}</count></item></root>