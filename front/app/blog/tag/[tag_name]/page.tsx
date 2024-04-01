import { Suspense } from 'react'
import ListLayout from '@/layouts/TagListLayout'

export default function tagPage(props) {
  const tagName: string = props.params.tag_name
  return (
    <div>
      <Suspense fallback={<div>Loading...</div>}>
        <ListLayout title="All Posts" tagName={tagName} />
      </Suspense>
    </div>
  )
}
