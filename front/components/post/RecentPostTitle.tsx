import Typography from '@mui/material/Typography';
import { Grid } from '@mui/material';
const RecentPostTitle = ({ title }) => {
  return (
    <Grid container spacing={2}>
      <Grid item xs={12}>
        <Typography
          variant="body1"
          fontWeight={'bold'}
          gutterBottom
          style={{ overflow: 'hidden', textOverflow: 'ellipsis', whiteSpace: 'nowrap' }}
        >
          {title}
        </Typography>
      </Grid>
    </Grid>
  );
};

export default RecentPostTitle;
