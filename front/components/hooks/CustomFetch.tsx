export async function fetchWithCredentials<T>(
  url: string,
  method: string,
  accessToken: string,
  body?: T
): Promise<Response> {
  const requestOptions: RequestInit = {
    method: method,
    headers: {
      'Content-Type': 'application/json',
      Authorization: 'Bearer ' + accessToken,
    },
    credentials: 'include',
  }

  if (body !== undefined) {
    requestOptions.body = JSON.stringify(body)
  }

  return await fetch(url, requestOptions)
}

export async function fetchWithoutCredentials<T>(url: string, method: string, body?: T) {
  const requestOptions: RequestInit = {
    method: method,
    headers: {
      'Content-Type': 'application/json',
    },
    credentials: 'include',
  }

  if (body !== undefined) {
    requestOptions.body = JSON.stringify(body)
  }

  return await fetch(url, requestOptions)
}
