interface Content {
  contentId: number
  uuid: string
  title: string
  body: string
  complete: boolean
  views: number
  tags: Tags[]
  createdAt: Date
}

interface User {
  nickname: string
}

interface Tags {
  tagName: string
}

export interface ContentDetail {
  content: Content
  user: User
}
