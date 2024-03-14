interface Content {
  contentId: number
  title: string
  body: string
  complete: boolean
  views: number
  createdAt: Date
}

interface User {
  nickname: string
}

export interface ContentItem {
  content: Content
  user: User
}
