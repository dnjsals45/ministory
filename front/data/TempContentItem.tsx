interface Tag {
  tagName: string
}

interface Content {
  contentId: number
  uuid: string
  title: string
  body: string
  complete: boolean
  views: number
  createdAt: Date
  tags: Tag[]
}

interface User {
  nickname: string
}

export interface TempContentItem {
  content: Content
  user: User
}
