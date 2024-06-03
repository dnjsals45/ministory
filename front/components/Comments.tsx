'use client'

import { useEffect, useState } from 'react'
import { CommentItem } from '@/data/CommentItem'
import { Divider, List, ListItem, ListItemText } from '@mui/material'
import process from 'process'
import { fetchWithoutCredentials } from '@/components/hooks/CustomFetch'

async function fetchComment(id: number): Promise<{ data: CommentItem[] }> {
  const response = await fetchWithoutCredentials(
    process.env.NEXT_PUBLIC_BACKEND_URL + `/api/v1/contents/${id}/comments`,
    'GET'
  )
  return response.json()
}
export default function Comments({ contentId, refreshComments }) {
  const [comments, setComments] = useState<CommentItem[]>([])
  useEffect(() => {
    const commentData = async (contentId: number) => {
      const response = await fetchComment(contentId)
      setComments(response.data)
    }

    commentData(contentId)
  }, [contentId, refreshComments])

  return (
    <List sx={{ width: '100%', maxWidth: 1500, bgcolor: 'background.paper' }}>
      {comments.map((item, index) => (
        <div key={index}>
          <ListItem alignItems="flex-start">
            <ListItemText primary={item.comment.comment} />
          </ListItem>
          {index !== comments.length - 1 && <Divider variant="middle" component="li" />}
        </div>
      ))}
    </List>
  )
}
