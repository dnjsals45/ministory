'use client'

import MyPost from '@/layouts/MyPost'
import { ContentDetail } from '@/data/ContentDetail'
import { useContext, useEffect, useState } from 'react'
import Comments from '@/components/Comments'
import CommentInput from '@/components/CommentInput'
import process from 'process'
import { AuthContext } from '@/components/hooks/useAuth'
import { fetchWithoutAuthorization } from '@/components/hooks/CustomFetch'

const Layout = MyPost

async function fetchContentData(id: number): Promise<{ data: ContentDetail }> {
  const data = await fetchWithoutAuthorization(
    process.env.NEXT_PUBLIC_BACKEND_URL + `/api/v1/contents/${id}`,
    'GET'
  )

  return data.json()
}

export default function Content(props) {
  const id = props.params.id
  const [detail, setDetail] = useState<ContentDetail | undefined>(undefined)
  const [refreshComments, setRefreshComments] = useState(false)
  const { userInfo } = useContext(AuthContext)

  useEffect(() => {
    const contentData = async (id: number) => {
      const response = await fetchContentData(id)
      setDetail(response.data)
    }

    contentData(id)
  }, [id])

  const handleCommentAdded = () => {
    setRefreshComments((prev) => !prev)
  }

  return (
    <div>
      {detail && (
        <>
          <div>
            <Layout content={detail} userRole={userInfo?.role} />
          </div>
          <div>
            <Comments contentId={detail.content.contentId} refreshComments={refreshComments} />
            <CommentInput
              contentId={detail.content.contentId}
              onCommentAdded={handleCommentAdded}
            />
          </div>
        </>
      )}
    </div>
  )
}
