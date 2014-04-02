package programmateurs.views;

//Useful example of how to do list adaptors in android...

//public class QuipsListAdapter extends BaseAdapter {

//  private final Activity activity;
//  List<Quip> items ;
//
//  public QuipsListAdapter(Activity activity, List<Quip> items) {
//    this.activity = activity;
//    this.items = items;
//  }
//
//  @Override
//  public View getView(int position, View convertView, ViewGroup parent) {
//
//    LayoutInflater inflater = (LayoutInflater)  activity
//        .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
//    View rowView = inflater.inflate(R.layout.corqitemlayout, parent, false);
//    TextView header = (TextView) rowView.findViewById(R.id.corqHeader);
//    TextView body = (TextView) rowView.findViewById(R.id.corqText);
//    ImageView imageView = (ImageView) rowView.findViewById(R.id.corqImage);
//    final Quip q = items.get(position);
//    String headerText = q.getUser_first() + " " + q.getUser_last() +
//  " in " + q.getBuilding_name();
//
//    header.setText(headerText);
//    body.setText(q.getMessage() + " \n\n" + q.getDate().toString() + "    "
//  + q.getNumlikes() + " likes");
//
//    //if the name is in the first half of the alphabet, use one pic.
//     otherwise use other
//    String first = q.getUser_first();
//    if (first.equals("Marek")) {
//      imageView.setImageResource(R.drawable.marekpic);
//    } else if (first.equals("Currell")){
//      imageView.setImageResource(R.drawable.currellpic);
//    } else if (first.equals("Jenny")){
//      imageView.setImageResource(R.drawable.jennypic);
//    } else if (first.equals("Arnav")) {
//      imageView.setImageResource(R.drawable.arnavpic);
//    } else {
//      imageView.setImageResource(R.drawable.person_dark);
//    }
//    rowView.setOnClickListener(new OnClickListener() {
//    public void onClick(View v) {
//    Anchor.getInstance().showDialog(activity, "Details", q.getHumanString());
//    }
//    });
//    return rowView;
//  }
//
//@Override
//public int getCount() {
//  return items.size();
//}
//
//@Override
//public Object getItem(int position) {
//  return items.get(position);
//}
//
//@Override
//public long getItemId(int position) {
//  return position;
//}

// }
