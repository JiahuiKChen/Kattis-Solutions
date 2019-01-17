#include <iostream>
#include <map>
#include <algorithm>

using namespace std;

int main(){
    int total;
    cin >> total;
    map<string, int> seen;
    string line;
    // don't need the first line
    getline(cin, line);

    //getting all anagram counts of all words
    while (getline(cin, line)){
        std::sort(line.begin(), line.end());

        map<string, int>::iterator element = seen.find(line);
        if (element == seen.end()){
            seen.insert(pair <string, int> (line, 0));
        }
        else {
            if (element->second == 0){ element->second = 2;}
            else {element->second++;}
        }
    }

    // getting total number of non anagrams
    int ana = 0;
    map<string, int>::iterator it;
    for (it = seen.begin(); it != seen.end(); it ++){
        if (it->second > 0){
           ana += it->second;
        }
    }
    cout << total - ana;
    return 0;
}