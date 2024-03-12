interface Comment {
  comment: string
  createdAt: Date
  updatedAt: Date
  deletedAt: Date
}

interface User {
  nickname: string
}

export interface CommentItem {
  comment: Comment
  user: User
}
