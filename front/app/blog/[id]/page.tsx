'use client'

import MyPost from '@/layouts/MyPost'
import { ContentDetail } from '@/data/ContentDetail'
import { useContext, useEffect, useState } from 'react'
import Comments from '@/components/Comments'
import CommentInput from '@/components/CommentInput'
import process from 'process'
import { AuthContext } from '@/components/hooks/useAuth'

const Layout = MyPost

async function fetchContentData(id: number): Promise<{ data: ContentDetail }> {
  const data = await fetch(process.env.NEXT_PUBLIC_BACKEND_URL + `/api/v1/contents/${id}`, {
    method: 'GET',
    headers: {
      'Content-Type': 'application/json;charset=UTF-8',
    },
  })
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
    setRefreshComments((prev) => !prev) // 댓글 목록 새로고침 상태를 토글하여 Comments 컴포넌트에서 새로운 목록을 불러오도록 합니다.
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
